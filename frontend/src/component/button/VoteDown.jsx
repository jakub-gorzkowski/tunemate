import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faThumbsDown} from '@fortawesome/free-solid-svg-icons'


function VoteDown() {
    return (
        <>
            <div className={"hover:text-red-600"}>
                <FontAwesomeIcon icon={faThumbsDown}/>
            </div>
        </>
    );
}

export default VoteDown;