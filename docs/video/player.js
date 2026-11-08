"use strict";

const video = document.querySelector("#video");
const links = [...document.querySelectorAll("[data-time]")];
const chapters = links.map(link => ({ link, start: Number(link.dataset.time), title: link.querySelector("strong").textContent }));
const status = document.querySelector("#status");
let pendingSeek = null;

function formatTime(seconds) {
  const total = Math.max(0, Math.floor(seconds));
  return `${String(Math.floor(total / 60)).padStart(2, "0")}:${String(total % 60).padStart(2, "0")}`;
}

function updateChapter() {
  const now = video.currentTime;
  const active = chapters.filter(chapter => now + 0.002 >= chapter.start).at(-1);
  chapters.forEach(chapter => {
    if (chapter === active) chapter.link.setAttribute("aria-current", "true");
    else chapter.link.removeAttribute("aria-current");
  });
  document.querySelector("#current-chapter").textContent = active?.title || "Introduction";
  document.querySelector("#playback-clock").textContent = formatTime(now);
}

function seek(seconds, play = false) {
  if (!Number.isFinite(seconds) || seconds < 0) return;
  if (video.readyState < 1) {
    pendingSeek = { seconds, play };
    status.textContent = `Loading chapter at ${formatTime(seconds)}…`;
    video.load();
    return;
  }
  pendingSeek = null;
  const target = Number.isFinite(video.duration) ? Math.min(seconds, Math.max(0, video.duration - 0.01)) : seconds;
  video.currentTime = target;
  updateChapter();
  status.textContent = `Ready at ${formatTime(target)}. Press Play to watch.`;
  if (play) video.play().catch(() => { status.textContent = `Ready at ${formatTime(target)}. Press Play to watch.`; });
}

function readLink() {
  const value = new URL(location.href).searchParams.get("t");
  if (value !== null && value.trim() !== "") seek(Number(value));
  else seek(0);
}

chapters.forEach(({ link, start }) => {
  link.addEventListener("click", event => {
    if (event.ctrlKey || event.metaKey || event.shiftKey || event.altKey) return;
    event.preventDefault();
    const url = new URL(location.href);
    url.searchParams.set("t", String(start));
    history.pushState(null, "", url);
    seek(start, true);
  });
});
video.addEventListener("loadedmetadata", () => {
  if (pendingSeek) seek(pendingSeek.seconds, pendingSeek.play);
});
video.addEventListener("timeupdate", updateChapter);
video.addEventListener("seeked", updateChapter);
video.addEventListener("play", () => { status.textContent = "Playing. Select any chapter to jump to it."; });
video.addEventListener("pause", () => { status.textContent = "Paused. Press Play to continue."; });
video.addEventListener("error", () => { document.querySelector("#video-error").hidden = false; status.textContent = "Video unavailable."; });
window.addEventListener("popstate", readLink);
readLink();
