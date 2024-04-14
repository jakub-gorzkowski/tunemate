import Navigation from "../component/menu/Navigation";
import Search from "../component/menu/Search";
import ContentList from "../component/content/list/ContentList";

function Playlists() {
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
                    <ContentList name={"Your playlists"} type={'playlist'} size={6}/>
                    <ContentList name={"Liked playlists"} type={'playlist'} size={6}/>
                    <ContentList name={"Popular playlists"} type={'playlist'} size={12}/>
                </div>
            </div>
        </>
    );
}

export default Playlists;