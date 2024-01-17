/**
 * <h1>InputComponent</h1>
 * <p>{id, name, labelText, validationError, onChange}</p>
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
export const Input = (props) => {
  const { id, name, labelText, validationError, onChange, defaultValue } =
    props;
  const type = props.type === '' ? 'text' : props.type;

  return (
    <div className="mb-3">
      <label htmlFor={id} className="form-label">
        {labelText}
      </label>
      <input
        className={'form-control ' + (validationError ? 'is-invalid' : '')}
        id={id}
        name={name}
        type={type}
        onChange={onChange}
        defaultValue={defaultValue}
      />
      <div className={'invalid-feedback'}>{validationError}</div>
    </div>
  );
};
