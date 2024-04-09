import MenuButton from "./MenuButton";
import { faHome, faHeart, faStar, faMessage, faUser, faGear} from '@fortawesome/free-solid-svg-icons'

function Navigation() {
    const navigationBar = (
        <>
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
                "border-r-2",
                "border-menu"
            ].join(' ')}>
                <MenuButton buttonIcon={faHome} buttonText={"Home"}/>
                <MenuButton buttonIcon={faHeart} buttonText={"Favourites"}/>
                <MenuButton buttonIcon={faStar} buttonText={"Playlists"}/>
                <MenuButton buttonIcon={faMessage} buttonText={"Reviews"}/>
            </div>
            <div className={[
                "w-80",
                "bg-menu",
                "h-full",
                "border-r-2",
                "border-menu"
            ].join(' ')}>
            </div>
            <div className={[
                "w-80",
                "flex",
                "flex-col",
                "items-center",
                "bg-menu",
                "border-r-2",
                "border-menu"
            ].join(' ')}>
                <MenuButton buttonIcon={faUser} buttonText={"Profile"}/>
                <MenuButton buttonIcon={faGear} buttonText={"Settings"}/>
            </div>
        </>
    );

    return (
        <>
            <div className={[
                "flex",
                "flex-col",
                "h-screen",
                "w-80",
                "bg-menu",
                "py-3",
                "border-r-2",
                "border-menu",
                "justify-center",
                "items-center"
            ].join(' ')}>
                {navigationBar}
            </div>
            <div className={[
                "fixed",
                "flex",
                "flex-col",
                "h-screen",
                "w-80",
                "bg-menu",
                "py-3",
                "border-r-2",
                "border-menu",
                "justify-center",
                "items-center"
            ].join(' ')}>
                {navigationBar}
            </div>
        </>
    );
}

export default Navigation;