import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowRight} from '@fortawesome/free-solid-svg-icons'

function GoForward() {
    return (
        <>
            <a href={"/playlist/:id"}>
                <div className={"bg-genre hover:bg-page-hover w-12 h-8 flex items-center justify-center rounded-r-full text-menu opacity-90"}>
                    <FontAwesomeIcon icon={faArrowRight}/>
                </div>
            </a>
        </>
    );
}

export default GoForward;