import axios from "axios";
import {i18nInstance} from "../../locales/index.js";

export function activateUser(token) {
    return axios.patch(`/api/v1/users/${token}/active`, null, {
        headers: {
            "Accept-Language": i18nInstance.language
        }
    });
}