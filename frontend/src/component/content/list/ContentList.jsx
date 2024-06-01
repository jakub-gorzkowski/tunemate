import PropTypes from "prop-types";
import Release from "../list-item/Release";
import Artist from "../list-item/Artist";
import {useEffect, useState} from "react";
import axios from "axios";
import Genre from "../list-item/Genre";

function ContentList(props) {
    const [data, setData] = useState([]);


    useEffect(() => {
        if (props.type === 'this-week' || props.type === 'this-month') {
            axios.get(`http://localhost:8080/api/releases/${props.type}`)
                .then(response => {
                    setData(response.data);
                })
                .catch(error => {
                    console.error(error);
                });
        } else if (props.type === 'release') {
            axios.get(`http://localhost:8080/api/artists/get/${props.artistId}/albums`)
                .then(response => {
                    setData(response.data);
                })
                .catch(error => {
                    console.error(error);
                });
        } else if (props.type === 'genre') {
                axios.get(`http://localhost:8080/api/artists/get/${props.id}/genres`)
                    .then(response => {
                        setData(response.data);
                    })
                    .catch(error => {
                        console.error(error);
                    });
        } else {
            axios.get(`http://localhost:8080/api/artists/${props.type}/new`)
                .then(response => {
                    setData(response.data);
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }, [props.type]);



    return (
        props.type != null &&
        <>
            <div>
                <h1 className={[
                    "text-title",
                    "text-xl",
                    "font-medium",
                    "mx-5",
                    "my-2",
                ].join(' ')}>{props.name}</h1>
                <div className={[
                    "flex",
                    "flex-wrap",
                    "w-content"
                ].join(' ')}>
                    {data.slice(0, props.size).map((item, index) => {
                        if (props.type === 'this-week' || props.type === 'this-month' || props.type === 'release') {
                            return <Release key={index} data={item}/>
                        } else if (props.type === 'genre') {
                            return <Genre key={index} data={item}/>
                        } else {
                            return <Artist key={index} data={item}/>
                        }
                    })}

                </div>
            </div>
        </>
    );
}

ContentList.propTypes = {
    name: PropTypes.string,
    type: PropTypes.string,
    artistId: PropTypes.string
};

export default ContentList;