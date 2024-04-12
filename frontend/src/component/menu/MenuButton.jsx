import PropTypes from "prop-types";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

function MenuButton(props) {
    return (
        props.buttonText.length > 0 && props.buttonIcon && props.url &&
        <>
            <a href={props.url}>
                <div className={[
                    "w-72",
                    "h-12",
                    "text-menu",
                    "bg-menu",
                    "flex",
                    "items-center",
                    "rounded-md",
                    "my-1",
                    "hover:bg-menu-current"
                ].join(' ')}>

                    <p>
                        <FontAwesomeIcon icon={props.buttonIcon} className={[
                            "mx-3",
                            "items-center"
                        ].join(' ')}/>{props.buttonText}
                    </p>

                </div>
            </a>
        </>
    );
}

MenuButton.propTypes = {
    buttonIcon: PropTypes.object,
    buttonText: PropTypes.string,
    url: PropTypes.string
}

MenuButton.defaultProps = {
    buttonIcon: null,
    buttonText: null,
    url: null
}

export default MenuButton;