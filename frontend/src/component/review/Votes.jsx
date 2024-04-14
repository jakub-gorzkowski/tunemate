import VoteUp from "../button/VoteUp";
import VoteDown from "../button/VoteDown";

function Votes() {
    return (
        <>
            <div className={"flex flex-auto space-x-2 items-center text-white"}>
                <p>0</p>
                <VoteUp/>
                <VoteDown/>
            </div>
        </>
    );
}

export default Votes;