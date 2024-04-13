import Navigation from "../menu/Navigation";
import Search from "../menu/Search";
import ContentList from "../content/ContentList";

function HomePage() {
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
                <div className={"mt-20"}>
                    <ContentList name={"Popular this week"} type={'release'} size={6}/>
                    <ContentList name={"Popular this month"} type={'release'} size={6}/>
                    <ContentList name={"New in Genre"} type={'release'} size={6}/>
                    <ContentList name={"New in Genre"} type={'release'} size={6}/>
                    <ContentList name={"New in Genre"} type={'release'} size={6}/>
                </div>
            </div>
        </>
    );
}

export default HomePage;