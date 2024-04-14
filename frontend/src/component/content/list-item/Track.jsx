import Rating from "../rating/Rating";

function Track() {
    return (
        <>
            <div className={"px-4 py-1 w-track border-b border-menu flex flex-auto items-center hover:bg-page-hover"}>
                <div className={"w-full"}>
                    <h1>Track title</h1>
                    <h2 className={"font-thin text-menu"}>Artist name</h2>
                </div>
                <div className={"w-full"}/>
                <Rating/>
                <h2 className={"font-thin text-menu ml-16"}>00:00</h2>
            </div>
        </>
    );
}

export default Track;