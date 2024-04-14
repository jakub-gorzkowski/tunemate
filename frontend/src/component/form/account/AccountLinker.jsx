const input = [
    "w-96",
    "px-3",
    "py-0.5",
    "mt-1",
    "mb-4",
    "rounded-lg",
    "bg-search"
].join(' ');

function AccountLinker() {
    return (
        <>
            <div className={"mt-10"}>
                <h1 className={[
                    "text-title",
                    "text-xl",
                    "font-medium",
                    "mx-5",
                    "my-2",
                ].join(' ')}>Link your accounts</h1>
                <div className={[
                    "flex",
                    "flex-col",
                    "w-content"
                ].join(' ')}>
                    <form method={"post"} action={"#"} className={"mt-3 mx-5 text-white text-lg font-light"}>
                        <h1>Spotify</h1>
                        <input type={"text"} placeholder={"Enter address"} className={input}/>
                        <h1>Tidal</h1>
                        <input type={"text"} placeholder={"Enter address"} className={input}/>
                        <h1>Deezer</h1>
                        <input type={"text"} placeholder={"Enter address"} className={input}/>
                        <h1>Soundcloud</h1>
                        <input type={"text"} placeholder={"Enter address"} className={input}/>
                        <div className={"my-2"}>
                            <input type={"submit"} value={"Save"} className={["w-24", "font-medium", "mr-3", "py-0.5", "rounded-lg", "bg-blue-600", "hover:bg-blue-500"].join(' ')}/>
                            <input type={"reset"} value={"Cancel"} className={["w-24", "font-thin", "mr-3", "py-0.5", "rounded-lg", "bg-page-hover", "hover:bg-gray-400"].join(' ')}/>
                        </div>
                    </form>
                </div>
            </div>
        </>
    );
}

export default AccountLinker;