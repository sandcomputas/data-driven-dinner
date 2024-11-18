
const renderYoutube = (link: string) => {
    const youtubePostFix = link.split("=").at(-1)
    const embeddedLink = `https://www.youtube-nocookie.com/embed/${youtubePostFix}`
    return (
        <iframe
            width="560" height="315"
            src={embeddedLink}
            title="YouTube video player" frameBorder="0"
            allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
            referrerPolicy="strict-origin-when-cross-origin" allowFullScreen>
        </iframe>
    )
}

export default renderYoutube;