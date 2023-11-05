import { activateUser } from './api.js';
import { Alert } from '@/shared/component/Alert.jsx';
import { Spinner } from '@/shared/component/Spinner.jsx';
import { useRouteParamApiRequest } from '@/shared/hooks/useRouteParamApiRequest.js';

export function Activation() {
  const { apiProgress, data, error } = useRouteParamApiRequest(
    'token',
    activateUser,
  );

  return (
    <div>
      {apiProgress && (
        <Alert
          message={<Spinner big={true} />}
          center={true}
          styleType={'success'}
        />
      )}
      {data?.message && <Alert message={data.message} styleType={'success'} />}
      {error && <Alert message={error} styleType={'danger'} />}
    </div>
  );
}
