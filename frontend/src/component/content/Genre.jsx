
function Genre() {
    return (
        <>
            <a href={"#"}>
                <div className={[
                    "flex",
                    "flex-col",
                    "items-center",
                    "m-1",
                    "p-4",
                    "rounded-lg",
                    "bg-genre",
                    "hover:bg-page-hover"
                ].join(' ')}>
                    <div className={[
                        "w-40",
                    ].join(' ')}>
                        <p className={[
                            "text-menu",
                            "font-light"
                        ].join(' ')}>Genre</p>
                    </div>
                </div>
            </a>
        </>
    );
}

export default Genre;