<?php

namespace AppBundle\EventListener;

use Avanzu\AdminThemeBundle\Event\SidebarMenuEvent;
use Avanzu\AdminThemeBundle\Model\MenuItemModel;
use Symfony\Component\HttpFoundation\Request;

class SidebarListener
{
    public function onSetupMenu(SidebarMenuEvent $event)
    {
        $request = $event->getRequest();

        foreach ($this->getMenu($request) as $item) {
            $event->addItem($item);
        }

    }

    /**
     * Get the sidebar menu
     *
     * @param Request $request
     * @return mixed
     */
    protected function getMenu(Request $request)
    {
        $earg = array();
        $rootItems = array(
            $dash = new MenuItemModel('dashboard', 'Dashboard', 'homepage', $earg, 'fa fa-dashboard'),
            $users = new MenuItemModel('users', 'Users', 'users', $earg, 'fa fa-user'),
            $faq = new MenuItemModel('faq', 'FAQ', 'admin_faq', $earg, 'fa fa-dashboard'),
            $colocation = new MenuItemModel('colocation', 'Colocation', 'admin_reclamation', $earg, 'fa fa-dashboard'),
            $covoiturage = new MenuItemModel('reclamation', 'Reclamation', 'admin_covoiturage', $earg, 'fa fa-dashboard'),
            $education = new MenuItemModel('education', 'Education & Aide', 'admin_aide', $earg, 'fa fa-dashboard'),
            $reclamation = new MenuItemModel('evenement', 'Evenement', 'admin_evenement', $earg, 'fa fa-dashboard'),

            $form = new MenuItemModel('forms', 'Forms', 'avanzu_admin_form_demo', $earg, 'fa fa-edit'),
            $widgets = new MenuItemModel('widgets', 'Widgets', 'avanzu_admin_demo', $earg, 'fa fa-th', 'new'),
            $ui = new MenuItemModel('ui-elements', 'UI Elements', '', $earg, 'fa fa-laptop')
        );

        $ui->addChild(new MenuItemModel('ui-elements-general', 'General', 'avanzu_admin_ui_gen_demo', $earg))
            ->addChild($icons = new MenuItemModel('ui-elements-icons', 'Icons', 'avanzu_admin_ui_icon_demo', $earg));

        return $this->activateByRoute($request->get('_route'), $rootItems);

    }

    /**
     * Set current menu item to be active
     *
     * @param $route
     * @param $items
     * @return mixed
     */
    protected function activateByRoute($route, $items)
    {

        foreach ($items as $item) {
            /** @var $item MenuItemModel */
            if ($item->hasChildren()) {
                $this->activateByRoute($route, $item->getChildren());
            } else {
                if ($item->getRoute() == $route) {
                    $item->setIsActive(true);
                }
            }
        }

        return $items;
    }


}