import GoBackward from "../button/GoBackward";
import GoForward from "../button/GoForward";

function PlaylistNavigation() {
    return (
        <>
            <div className={"flex flex-auto mt-4"}>
                <GoBackward/>
                <GoForward/>
            </div>
        </>
    );
}

export default PlaylistNavigation;