// eslint-disable-next-line no-unused-vars
import React, {Component} from "react";
import {useParams} from "react-router-dom";
import {getUser} from "@/pages/User/api.js";
import defaultImage from "@/assets/profile.png"
import {Alert} from "@/shared/component/Alert.jsx";
import {Spinner} from "@/shared/component/Spinner.jsx";

export class UserPageClassComponent extends Component {


    state = {
        user: undefined, errorMessage: undefined, apiProgress: false
    }

    async componentDidMount() {
        this.setState({apiProgress: true});
        try {
            const response = await getUser(this.props.userId);
            console.table(response.data);
            this.setState({user: response.data});
        } catch (err) {
            if (err.response.status === 400) {
                this.setState({user: undefined, errorMessage: err.response.data.message});
            } else {
                this.setState({user: undefined, errorMessage: err.message});
            }
        } finally {
            this.setState({apiProgress: false});
        }
    }

    render() {

        let {user, errorMessage, apiProgress} = this.state;
        if (apiProgress) {
            return <Spinner big={true}/>
        }
        if (user) {
            return <div className="card" style={{'width': '100%'}}>
                <img src={defaultImage} className="mx-auto" alt="profile" height={100} width={100}/>
                <div className="card-body">
                    <h5 className="card-title">{user?.username}</h5>
                    <p className="card-text">{user?.email}</p>
                    <a href="#" className="btn btn-primary">{user?.image}</a>
                </div>
            </div>;
        }

        return <div>
            {errorMessage && (<Alert styleType={'danger'} message={errorMessage} center={false}/>)}
        </div>
    }
}

export function ForUserClassComponent() {
    const {userId} = useParams();
    return <UserPageClassComponent userId={userId}/>;
}