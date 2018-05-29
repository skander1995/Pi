<?php

namespace EducBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use EducBundle\Entity\Aide;
use EducBundle\Form\Type\AideType;
use EducBundle\Form\Type\AideFilterType;
use Symfony\Component\Form\FormInterface;
use Doctrine\ORM\QueryBuilder;

/**
 * Aide controller.
 *
 */
class AideController extends Controller
{
    /**
     * Lists all Aide entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $form = $this->createForm('EducBundle\Form\Type\AideFilterType');
        if (!is_null($response = $this->saveFilter($form, 'aide', 'admin_aide'))) {
            return $response;
        }
        $qb = $em->getRepository('EducBundle:Aide')->createQueryBuilder('a');
        $paginator = $this->filter($form, $qb, 'aide');
        return $this->render('EducBundle:Aide:index.html.twig', array(
            'form'      => $form->createView(),
            'paginator' => $paginator,
        ));
    }

    /**
     * Finds and displays a Aide entity.
     *
     */
    public function showAction(Aide $aide)
    {
        $deleteForm = $this->createDeleteForm($aide->getId(), 'admin_aide_delete');

        return $this->render('EducBundle:Aide:show.html.twig', array(
            'aide' => $aide,
            'delete_form' => $deleteForm->createView(),        ));
    }

    /**
     * Displays a form to create a new Aide entity.
     *
     */
    public function newAction()
    {
        $aide = new Aide();
        $form = $this->createForm('EducBundle\Form\AideType', $aide);

        return $this->render('EducBundle:Aide:new.html.twig', array(
            'aide' => $aide,
            'form'   => $form->createView(),
        ));
    }

    /**
     * Creates a new Aide entity.
     *
     */
    public function createAction(Request $request)
    {
        $aide = new Aide();
        $form = $this->createForm('EducBundle\Form\AideType', $aide);
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $aide->setDatepub(new \DateTime());
            $em->persist($aide);
            $em->flush();

            return $this->redirect($this->generateUrl('admin_aide_show', array('id' => $aide->getId())));
        }

        return $this->render('EducBundle:Aide:new.html.twig', array(
            'aide' => $aide,
            'form'   => $form->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing Aide entity.
     *
     */
    public function editAction(Aide $aide)
    {
        $editForm = $this->createForm('EducBundle\Form\AideType', $aide, array(
            'action' => $this->generateUrl('admin_aide_update', array('id' => $aide->getId())),
            'method' => 'PUT',
        ));
        $deleteForm = $this->createDeleteForm($aide->getId(), 'admin_aide_delete');

        return $this->render('EducBundle:Aide:edit.html.twig', array(
            'aide' => $aide,
            'edit_form'   => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Edits an existing Aide entity.
     *
     */
    public function updateAction(Aide $aide, Request $request)
    {
        $editForm = $this->createForm('EducBundle\Form\AideType', $aide, array(
            'action' => $this->generateUrl('admin_aide_update', array('id' => $aide->getId())),
            'method' => 'PUT',
        ));
        if ($editForm->handleRequest($request)->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirect($this->generateUrl('admin_aide_edit', array('id' => $aide->getId())));
        }
        $deleteForm = $this->createDeleteForm($aide->getId(), 'admin_aide_delete');

        return $this->render('EducBundle:Aide:edit.html.twig', array(
            'aide' => $aide,
            'edit_form'   => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }


    /**
     * Save order.
     *
     */
    public function sortAction($field, $type)
    {
        $this->setOrder('aide', $field, $type);

        return $this->redirect($this->generateUrl('admin_aide'));
    }

    /**
     * @param string $name  session name
     * @param string $field field name
     * @param string $type  sort type ("ASC"/"DESC")
     */
    protected function setOrder($name, $field, $type = 'ASC')
    {
        $this->container->get('request_stack')->getCurrentRequest()
        ->getSession()->set('sort.' . $name, array('field' => $field, 'type' => $type));
    }

    /**
     * @param  string $name
     * @return array
     */
    protected function getOrder($name)
    {
        $session = $this->container->get('request_stack')->getCurrentRequest()->getSession();

        return $session->has('sort.' . $name) ? $session->get('sort.' . $name) : null;
    }

    /**
     * @param QueryBuilder $qb
     * @param string       $name
     */
    protected function addQueryBuilderSort(QueryBuilder $qb, $name)
    {
        $alias = current($qb->getDQLPart('from'))->getAlias();
        if (is_array($order = $this->getOrder($name))) {
            $qb->orderBy($alias . '.' . $order['field'], $order['type']);
        }
    }

    /**
     * Save filters
     *
     * @param  FormInterface $form
     * @param  string        $name   route/entity name
     * @param  string        $route  route name, if different from entity name
     * @param  array         $params possible route parameters
     * @return Response
     */
    protected function saveFilter(FormInterface $form, $name, $route = null, array $params = null)
    {
        $request = $this->container->get('request_stack')->getCurrentRequest();
        $url = $this->generateUrl($route ?: $name, is_null($params) ? array() : $params);
        if ($request->query->has('submit-filter') && $form->handleRequest($request)->isValid()) {
            $request->getSession()->set('filter.' . $name, $request->query->get($form->getName()));

            return $this->redirect($url);
        } elseif ($request->query->has('reset-filter')) {
            $request->getSession()->set('filter.' . $name, null);

            return $this->redirect($url);
        }
    }

    /**
     * Filter form
     *
     * @param  FormInterface                                       $form
     * @param  QueryBuilder                                        $qb
     * @param  string                                              $name
     * @return \Knp\Component\Pager\Pagination\PaginationInterface
     */
    protected function filter(FormInterface $form, QueryBuilder $qb, $name)
    {
        if (!is_null($values = $this->getFilter($name))) {
            if ($form->submit($values)->isValid()) {
                $this->get('lexik_form_filter.query_builder_updater')->addFilterConditions($form, $qb);
            }
        }

        // possible sorting
        $this->addQueryBuilderSort($qb, $name);
        return $this->get('knp_paginator')->paginate($qb, $this->container->get('request_stack')->getCurrentRequest()->query->get('page', 1), 20);
    }

    /**
     * Get filters from session
     *
     * @param  string $name
     * @return array
     */
    protected function getFilter($name)
    {
        return $this->container->get('request_stack')->getCurrentRequest()->getSession()->get('filter.' . $name);
    }

    /**
     * Deletes a Aide entity.
     *
     */
    public function deleteAction(Aide $aide, Request $request)
    {
        $form = $this->createDeleteForm($aide->getId(), 'admin_aide_delete');
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($aide);
            $em->flush();
        }

        return $this->redirect($this->generateUrl('admin_aide'));
    }

    /**
     * Create Delete form
     *
     * @param integer                       $id
     * @param string                        $route
     * @return \Symfony\Component\Form\Form
     */
    protected function createDeleteForm($id, $route)
    {
        return $this->createFormBuilder(null, array('attr' => array('id' => 'delete')))
            ->setAction($this->generateUrl($route, array('id' => $id)))
            ->setMethod('DELETE')
            ->getForm()
            ;
    }

}
