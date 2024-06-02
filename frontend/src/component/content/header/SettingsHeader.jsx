function SettingsHeader({ user }) {
    if (!user) {
        return <div>Loading...</div>;
    }

    return (
        <>
            <div className={"w-full flex flex-col items-center mt-36"}>
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
                    ].join(' ')} style={{
                        backgroundImage: `url(${user.photoUrl})`,
                        backgroundSize: 'cover',
                        backgroundPosition: 'center'
                    }}></div>
                    <div className={"w-64 ml-4"}>
                        <h1 className={"text-4xl font-bold text-white"}>{user.username}</h1>
                    </div>
                </div>
                <div className={"w-content"}>
                    <div className={"flex mt-12 mx-3 mb-2"}>
                        <a href={"#"}><h3 className={"mx-3 text-white"}>Account details</h3></a>
                        <a href={"#"}><h3 className={"mx-3 text-white"}>Link your accounts</h3></a>
                    </div>
                </div>
            </div>
        </>
    );
}

export default SettingsHeader;
