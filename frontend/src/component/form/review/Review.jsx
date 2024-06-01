function Review() {
    return (
        <>
            <div className={"mt-10"}>
                <h1 className={[
                    "text-title",
                    "text-xl",
                    "font-medium",
                    "mx-4",
                    "my-2"
                ].join(' ')}>Share your thoughts</h1>
                <div className={"p-4 flex flex-col"}>
                    <form method={"post"}>
                        <textarea placeholder={"Enter your comment here"}
                                  className={"bg-genre w-review mt-4 p-4 h-40 min-h-24 max-h-56 rounded-xl"}/>
                        <div className={"flex flex-auto justify-end mt-4"}>
                            <input type={"submit"} value={"Post review"}
                                   className={["w-24", "font-medium", "mr-3", "py-0.5", "rounded-lg", "bg-blue-600", "hover:bg-blue-500"].join(' ')}/>
                            <input type={"reset"} value={"Cancel"}
                                   className={["w-24", "font-thin", "py-0.5", "rounded-lg", "bg-page-hover", "hover:bg-gray-400"].join(' ')}/>
                        </div>
                    </form>
                </div>
            </div>
        </>
    );
}

export default Review;