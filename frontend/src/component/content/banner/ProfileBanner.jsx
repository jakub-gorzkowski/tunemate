import PropTypes from "prop-types";
import UserHeader from "../header/UserHeader";
import SettingsHeader from "../header/SettingsHeader";

function ProfileBanner(props) {
    const header = (
        props.type === 'profile' ? <UserHeader/> :
        props.type === 'settings' ? <SettingsHeader/> : null
    );

    return (
        props.type != null &&
        <>
            <div className={[
                "flex",
                "flex-auto",
                "items-center",
                "h-96",
                "w-full",
                "bg-menu",
                "border-b",
                "border-menu"
            ].join(' ')}>
                {header}
            </div>
        </>
    );
}

ProfileBanner.propTypes = {
    type: PropTypes.string
};

export default ProfileBanner;