export function Spinner(props) {
    const {big} = props;
    return (<span className={'spinner-border spinner-border' + (big ? '' : '-sm')}></span>)
}