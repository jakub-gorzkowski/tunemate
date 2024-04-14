import Rating from "../content/rating/Rating";
import Votes from "./Votes";

function Review() {
    return (
        <>
            <div className={"px-4 py-3 my-6 w-review border-b border-menu flex flex-col items-center hover:bg-page-hover"}>
                <div className={"w-full"}>
                    <div className={"flex flex-auto items-center"}>
                        <div className={"flex flex-col mb-2"}>
                            <h1 className={"text-white text-xl mb-2"}>Username</h1>
                            <Rating/>
                        </div>
                        <div className={"w-full"}/>
                        <Votes/>
                    </div>
                    <h2 className={"font-thin text-menu"}>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc ultricies, odio quis elementum accumsan, ante diam commodo enim, ac placerat velit mauris eget nisi.
                        Curabitur eu odio mauris. Fusce blandit, urna vel lacinia consequat, sem sem lacinia urna, non porttitor elit odio eu eros.
                        Duis ultrices ultricies porttitor. Nullam tempus cursus ipsum nec sollicitudin. Sed egestas et quam nec vulputate. Aliquam erat volutpat.
                    </h2>
                </div>
            </div>
        </>
    );
}

export default Review;