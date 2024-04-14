import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faThumbsUp} from '@fortawesome/free-solid-svg-icons'


function VoteUp() {
    return (
        <>
            <div className={"hover:text-green-600"}>
                <FontAwesomeIcon icon={faThumbsUp}/>
            </div>
        </>
    );
}

export default VoteUp;