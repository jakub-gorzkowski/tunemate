import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ContentList from "../component/content/list/ContentList";
import ArtistBanner from "../component/content/banner/ArtistBanner";
import TrackList from "../component/content/list/TrackList";

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
                        <ArtistBanner/>
                    </div>
                    <div className={"flex flex-col items-center justify-center my-16"}>
                        <TrackList title={"Popular tracks"} length={10}/>
                        <ContentList name={"Latest releases"} type={'release'} size={6}/>
                        <ContentList name={"Genres"} type={'genre'} size={4}/>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Profile;