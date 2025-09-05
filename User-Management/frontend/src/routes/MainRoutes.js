import { lazy } from 'react';

// project import
import Loadable from 'components/Loadable';
import MainLayout from 'layout/MainLayout';

// render - sample page
const AlbumsPage = Loadable(lazy(() => import('pages/albums/albums.js')));
const AboutPage = Loadable(lazy(() => import('pages/staticPages/about.js')));


// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
  path: '/',
  element: <MainLayout />,
  children: [
    {
      path: '/',
      element: <AlbumsPage />
    },
    {
      path: '/about',
      element: <AboutPage />
    },
  ]
};

export default MainRoutes;
