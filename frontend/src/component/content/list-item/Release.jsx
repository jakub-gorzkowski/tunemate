
function Release() {
    return (
        <>
            <a href={"/release/:id"}>
                <div className={[
                    "flex",
                    "flex-col",
                    "items-center",
                    "m-1",
                    "p-4",
                    "h-64",
                    "rounded-2xl",
                    "hover:bg-page-hover"
                ].join(' ')}>
                    <div className={[
                        "w-40",
                        "h-40",
                        "bg-cover-placeholder",
                        "rounded-lg"
                    ].join(' ')}></div>

                    <h1 className={[
                        "text-lg",
                        "text-title",
                        "font-medium",
                        "mt-2"
                    ].join(' ')}>Release title</h1>

                    <p className={[
                        "text-sm",
                        "text-menu",
                        "font-thin"
                    ].join(' ')}>Artist name</p>
                </div>
            </a>
        </>
    );
}

export default Release;