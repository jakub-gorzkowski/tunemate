import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ContentList from "../component/content/list/ContentList";
import TrackList from "../component/content/list/TrackList";
import ReleaseBanner from "../component/content/banner/ReleaseBanner";

function Release() {
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
                    <ReleaseBanner/>
                    <div className={"flex flex-col items-center my-16 text-white"}>
                        <TrackList length={12}/>
                        <ContentList name={"Genres"} type={"genre"} size={2}/>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Release;