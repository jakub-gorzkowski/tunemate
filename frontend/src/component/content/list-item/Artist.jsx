
function Artist() {
    return (
        <>
            <a href={"#"}>
                <div className={[
                    "flex",
                    "flex-col",
                    "items-center",
                    "m-1",
                    "pt-4",
                    "px-4",
                    "h-56",
                    "rounded-2xl",
                    "hover:bg-page-hover"
                ].join(' ')}>
                    <div className={[
                        "w-40",
                        "h-40",
                        "bg-cover-placeholder",
                        "rounded-full"
                    ].join(' ')}></div>

                    <h1 className={[
                        "text-lg",
                        "text-title",
                        "font-medium",
                        "mt-2"
                    ].join(' ')}>Artist</h1>
                </div>
            </a>
        </>
    );
}

export default Artist;