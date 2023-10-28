import defaultImage from "@/assets/profile.png";
import {useTranslation} from "react-i18next";
import {useNavigate} from "react-router-dom";

export function ProfileCard({user}) {
    let navigate = useNavigate();
    const {t} = useTranslation();
    return (<div className="card" style={{'width': '100%'}}>
        <img src={defaultImage} className="mx-auto" alt="profile" height={100} width={100}/>
        <div className="card-body">
            <h5 className="card-title">{user?.username}</h5>
            <p className="card-text">{user?.email}</p>
            <a href="#" className="btn btn-primary" onClick={() => {
                navigate(-1);
            }}>{t('go-back')}</a>
        </div>
    </div>);
}