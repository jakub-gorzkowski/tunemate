import ArtistHeader from "../header/ArtistHeader";

function ArtistBanner() {
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
                <ArtistHeader/>
            </div>
        </>
    );
}


export default ArtistBanner;