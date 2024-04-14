import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faArrowLeft} from '@fortawesome/free-solid-svg-icons'

function GoForward() {
    return (
        <>
            <a href={"/playlist/:id"}>
                <div className={"bg-genre hover:bg-page-hover w-12 h-8 flex items-center justify-center rounded-l-full text-menu opacity-90"}>
                    <a href={"/playlist/:id"}><FontAwesomeIcon icon={faArrowLeft}/></a>
                </div>
            </a>
        </>
    );
}

export default GoForward;