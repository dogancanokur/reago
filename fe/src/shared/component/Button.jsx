import {Spinner} from "@/shared/component/Spinner.jsx";

export function Button(props) {
    const {disabled, type, className, text, apiProgress} = props;

    return (<button disabled={disabled} type={type || 'submit'}
                    className={'btn ' + className}>{apiProgress && <Spinner/>} {text}</button>);
}