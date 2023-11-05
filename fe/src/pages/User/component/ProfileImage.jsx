import defaultProfileImage from '@/assets/profile.png';

export function ProfileImage({ width, height, alt, tempImage, image }) {
  return (
    <img
      src={tempImage || image || defaultProfileImage}
      width={width || height || '24'}
      height={height || width || '24'}
      className={'rounded-circle shadow-sm'}
      alt={alt || ''}
    />
  );
}
