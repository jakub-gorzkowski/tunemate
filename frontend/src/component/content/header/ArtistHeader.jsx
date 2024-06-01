function ArtistHeader({ artist }) {
    if (!artist) {
        return <div>Loading...</div>;
    }

    return (
        <>
            <div className={"w-full flex flex-col items-center mt-44"}>
                <div className={[
                    "w-content",
                    "flex",
                    "items-center"
                ].join(' ')}>
                    <div className={[
                        "w-40",
                        "h-40",
                        "bg-cover",
                        "rounded-full"
                    ].join(' ')} style={{ backgroundImage: `url(${artist.photoUrl})` }}></div>
                    <div className={"w-64 ml-4 text-white flex flex-col my-1"}>
                        <h1 className={"text-4xl font-bold"}>{artist.name}</h1>
                        <h2>{artist.followerCount} Followers</h2>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ArtistHeader;