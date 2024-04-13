import Navigation from "../menu/Navigation";
import Search from "../menu/Search";
import ContentList from "../content/ContentList";
import ProfileBanner from "../content/ProfileBanner";

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
                        <ProfileBanner/>
                    </div>
                    <div className={"flex flex-col items-center justify-center mt-16"}>
                        <ContentList name={"Genres"} type={'genre'} size={12}/>
                        <ContentList name={"Favourites"} type={'release'} size={6}/>
                        <ContentList name={"Artists"} type={'artist'} size={6}/>
                        <ContentList name={"Playlists"} type={'playlist'} size={6}/>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Profile;