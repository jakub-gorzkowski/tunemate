import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ContentList from "../component/content/list/ContentList";

function Favourites() {
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
                <div className={"my-20"}>
                    <ContentList name={"Favourite releases"} type={'release'} size={6}/>
                    <ContentList name={"Favourite artists"} type={'artist'} size={6}/>
                    <ContentList name={"Favourite playlists"} type={'playlist'} size={6}/>
                    <ContentList name={"Favourite genres"} type={'genre'} size={12}/>
                </div>
            </div>
        </>
    );
}

export default Favourites;