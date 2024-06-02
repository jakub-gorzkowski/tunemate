import PlaylistHeader from "../header/PlaylistHeader";

function PlaylistBanner({ playlist }) {
    return (
        <>
            <div className={[
                "flex",
                "flex-auto",
                "items-center",
                "h-96",
                "w-full",
                "bg-menu",
                "border-b",
                "border-menu"
            ].join(' ')}>
                <PlaylistHeader playlist={playlist}/>
            </div>
        </>
    );
}

export default PlaylistBanner;