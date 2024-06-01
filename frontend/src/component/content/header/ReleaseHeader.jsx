import { Icon } from '@iconify/react';

const icons = [
    "w-7",
    "h-7",
    "mr-2",
    "text-menu"
].join(' ');

function ReleaseHeader() {
    return (
        <>
            <div className={"w-full flex flex-col items-center mt-36"}>
                <div className={[
                    "w-content",
                    "flex",
                    "items-end"
                ].join(' ')}>
                    <div className={[
                        "w-40",
                        "h-40",
                        "bg-cover-placeholder",
                        "rounded-lg"
                    ].join(' ')}></div>
                    <div className={"w-64 ml-4 text-white"}>
                        <h1 className={"text-4xl font-bold"}>Release title</h1>
                        <a href={"#"} className={"flex my-1"}><h2>Artist name</h2></a>
                        <p>Available on:</p>
                        <div className={"flex my-1"}>
                            <a href={"#"}><Icon icon={"mdi:spotify"} className={icons + " hover:text-green-500"}/></a>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default ReleaseHeader;