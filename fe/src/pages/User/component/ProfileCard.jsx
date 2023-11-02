import defaultImage from "@/assets/profile.png";
import {useTranslation} from "react-i18next";
import {Link, useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";

export function ProfileCard({user}) {

    const authState = useSelector((store) => store.auth);
    // const authState = useAuthState();

    let navigate = useNavigate();
    const {t} = useTranslation();
    return (<>
        <div className={'text-end mb-2'}>
            <Link to="#" className="btn btn-secondary" onClick={() => {
                navigate(-1);
            }}>{t('go-back')}</Link>
        </div>
        <div className="card" style={{'width': '100%'}}>
            <img src={defaultImage} className="mx-auto" alt="profile" height={100} width={100}/>
            <div className="card-body">
                <div className={'text-center'}>
                    <h5 className="card-title">{user?.id} - {user?.username}</h5>
                    <p className="card-text">{user?.email}</p>
                    {authState.id === user?.id && <a href="#" className="ml-2 btn btn-primary" onClick={() => {
                    }}>{t('edit')}</a>}
                </div>
            </div>
        </div>
    </>);
}