import Search from "../menu/Search";
import ContentList from "../content/ContentList";

function HomePage() {
    return (
        <>
            <div className={[
                "bg-page",
                "w-screen",
                "flex",
                "flex-auto",
                "justify-center"].join(' ')}>
                <Search/>
                <div className={"flex-col mt-20"}>
                    <ContentList name={"Popular this week"}/>
                    <ContentList name={"Popular this month"}/>
                    <ContentList name={"New in Genre"}/>
                    <ContentList name={"New in Genre"}/>
                    <ContentList name={"New in Genre"}/>
                </div>
            </div>
        </>
    );
}

export default HomePage;