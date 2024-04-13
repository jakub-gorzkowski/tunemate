import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ProfileBanner from "../component/content/banner/ProfileBanner";
import AccountDetails from "../component/form/AccountDetails";
import AccountLinker from "../component/form/AccountLinker";

function Profile() {
    return (
        <>
            <Navigation/>
            <div className={[
                "bg-page",
                "w-screen",
                "ml-80",
                "flex",
                "flex-auto",
                "justify-center"].join(' ')}>
                <Search/>
                <div className={"flex-col w-full"}>
                    <div className={"flex-col w-full"}>
                        <ProfileBanner type={"settings"}/>
                    </div>
                    <div className={"flex flex-col items-center justify-center my-16"}>
                        <AccountDetails/>
                        <AccountLinker/>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Profile;