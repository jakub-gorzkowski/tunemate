
function ArtistHeader() {
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
                        "bg-cover-placeholder",
                        "rounded-full"
                    ].join(' ')}></div>
                    <div className={"w-64 ml-4 text-white flex flex-col my-1"}>
                        <h1 className={"text-4xl font-bold"}>Artist</h1>
                        <h2>0 Followers</h2>
                        <h2>0 Listeners in the last month</h2>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ArtistHeader;