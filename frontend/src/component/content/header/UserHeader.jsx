import { Icon } from '@iconify/react';

const icons = [
    "w-7",
    "h-7",
    "mr-2",
    "text-menu"
].join(' ');

const profileSections = [
    "mx-3",
    "text-white"
].join(' ');

function UserHeader() {
    return (
        <>
            <div className={"w-full flex flex-col items-center mt-36"}>
                <div className={[
                    "w-content",
                    "flex",
                    "items-end"
                ].join(' ')}>
                    <div className={[
                        "w-40",
                        "h-40",
                        "bg-cover-placeholder",
                        "rounded-full"
                    ].join(' ')}></div>
                    <div className={"w-64 ml-4"}>
                        <h1 className={"text-4xl font-bold text-white"}>Username</h1>
                        <div className={"text-white flex my-1"}>
                            <a href={"#"}><h2>0 Followers</h2></a>
                            <a href={"#"}><h2 className={"ml-2"}>0 Followed</h2></a>
                        </div>

                        <div className={"flex my-1"}>
                            <a href={"#"}><Icon icon={"mdi:spotify"} className={icons + " hover:text-green-500"}/></a>
                            <a href={"#"}><Icon icon={"simple-icons:tidal"} className={icons + " hover:text-white"}/></a>
                            <a href={"#"}><Icon icon={"arcticons:deezer"} className={icons + " hover:text-purple-500"}/></a>
                            <a href={"#"}><Icon icon={"akar-icons:soundcloud-fill"} className={icons + " hover:text-orange-500"}/></a>
                        </div>
                    </div>
                </div>
                <div className={"w-content"}>
                    <div className={"flex mt-12 mx-3 mb-2"}>
                        <a href={"#"}><h3 className={profileSections}>Genres</h3></a>
                        <a href={"#"}><h3 className={profileSections}>Favourites</h3></a>
                        <a href={"#"}><h3 className={profileSections}>Artists</h3></a>
                        <a href={"#"}><h3 className={profileSections}>Playlists</h3></a>
                        <a href={"#"}><h3 className={profileSections}>Reviews</h3></a>
                    </div>
                </div>
            </div>
        </>
    );
}

export default UserHeader;