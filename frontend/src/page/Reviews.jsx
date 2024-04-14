import Navigation from "../component/menu/Navigation";
import ReviewList from "../component/content/list/ReviewList";
import Search from "../component/menu/Search";

function Reviews() {
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
                <div className={"flex flex-col items-center my-16 text-white"}>
                    <ReviewList size={2} title={"Recent reviews of Username"}/>
                    <a href={"#"} className={"justify-end"}>View more</a>
                    <ReviewList size={2} title={"Recent reviews of Username"}/>
                    <a href={"#"}>View more</a>
                </div>
            </div>
        </>
    );
}

export default Reviews;