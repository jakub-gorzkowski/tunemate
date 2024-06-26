import ArtistHeader from "../header/ArtistHeader";

function ArtistBanner({ artist }) {
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
                <ArtistHeader artist={artist}/>
            </div>
        </>
    );
}

export default ArtistBanner;
