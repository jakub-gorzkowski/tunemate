import ReleaseHeader from "../header/ReleaseHeader";

function ReleaseBanner({ release }) {
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
                <ReleaseHeader release={release}/>
            </div>
        </>
    );
}

export default ReleaseBanner;