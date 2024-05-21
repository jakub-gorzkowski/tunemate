import MenuButton from "./MenuButton";
import { faHome, faHeart, faStar, faMessage, faUser, faGear, faDoorClosed} from '@fortawesome/free-solid-svg-icons'

function Navigation() {
    return (
        <>
            <div className={[
                "fixed",
                "flex",
                "flex-col",
                "h-screen",
                "w-80",
                "bg-menu",
                "py-3",
                "border-r",
                "border-menu",
                "justify-center",
                "items-center"
            ].join(' ')}>
                <div className={[
                    "w-80",
                    "h-full",
                    "bg-menu",
                ]}>
                    <img src="/image/logo.png" alt="Tunemate" className={[
                        "w-56"
                    ].join(' ')}/>
                </div>
                <div className={[
                    "w-80",
                    "pt-3",
                    "flex",
                    "flex-col",
                    "items-center",
                    "bg-menu",
                    "border-t",
                    "border-r",
                    "border-menu"
                ].join(' ')}>
                    <MenuButton buttonIcon={faHome} buttonText={"Home"} url={"/"}/>
                    <MenuButton buttonIcon={faHeart} buttonText={"Favourites"} url={"/favourites"}/>
                    <MenuButton buttonIcon={faStar} buttonText={"Playlists"} url={"/playlists"}/>
                    <MenuButton buttonIcon={faMessage} buttonText={"Reviews"} url={"/reviews"}/>
                </div>
                <div className={[
                    "w-80",
                    "bg-menu",
                    "h-full",
                    "border-r",
                    "border-menu"
                ].join(' ')}>
                </div>
                <div className={[
                    "w-80",
                    "flex",
                    "flex-col",
                    "items-center",
                    "bg-menu",
                    "border-r",
                    "border-menu"
                ].join(' ')}>
                    <MenuButton buttonIcon={faUser} buttonText={"Profile"} url={"/profile"}/>
                    <MenuButton buttonIcon={faGear} buttonText={"Settings"} url={"/settings"}/>
                    <MenuButton buttonIcon={faDoorClosed} buttonText={"Log out"} url={"/"} logout={true}/>
                </div>
            </div>
        </>
    );
}

export default Navigation;