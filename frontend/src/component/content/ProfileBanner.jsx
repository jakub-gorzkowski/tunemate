import UserHeader from "./UserHeader";

function ProfileBanner() {
    return (
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
                <UserHeader/>
            </div>
        </>
    );
}

export default ProfileBanner;