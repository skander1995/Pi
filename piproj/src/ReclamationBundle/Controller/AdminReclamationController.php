<?php

namespace ReclamationBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use ReclamationBundle\Entity\Reclamation;
use ReclamationBundle\Form\Type\ReclamationType;
use ReclamationBundle\Form\Type\ReclamationFilterType;
use Symfony\Component\Form\FormInterface;
use Doctrine\ORM\QueryBuilder;

/**
 * Reclamation controller.
 *
 */
class AdminReclamationController extends Controller
{
    /**
     * Lists all Reclamation entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $form = $this->createForm('ReclamationBundle\Form\Type\ReclamationFilterType');
        if (!is_null($response = $this->saveFilter($form, 'reclamation', 'admin_reclamation'))) {
            return $response;
        }
        $qb = $em->getRepository('ReclamationBundle:Reclamation')->createQueryBuilder('r');
        $paginator = $this->filter($form, $qb, 'reclamation');
                return $this->render('ReclamationBundle:Reclamation:index.html.twig', array(
            'form'      => $form->createView(),
            'paginator' => $paginator,
        ));
    }

    /**
     * Finds and displays a Reclamation entity.
     *
     */
    public function showAction(Reclamation $reclamation)
    {
        $deleteForm = $this->createDeleteForm($reclamation->getId(), 'admin_reclmation_delete');

        return $this->render('ReclamationBundle:Reclamation:show.html.twig', array(
            'reclamation' => $reclamation,
            'delete_form' => $deleteForm->createView(),        ));
    }

    /**
     * Displays a form to create a new Reclamation entity.
     *
     */
    public function newAction()
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(new ReclamationType(), $reclamation);

        return $this->render('ReclamationBundle:Reclamation:new.html.twig', array(
            'reclamation' => $reclamation,
            'form'   => $form->createView(),
        ));
    }

    /**
     * Creates a new Reclamation entity.
     *
     */
    public function createAction(Request $request)
    {
        $reclamation = new Reclamation();
        $form = $this->createForm(new ReclamationType(), $reclamation);
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($reclamation);
            $em->flush();

            return $this->redirect($this->generateUrl('admin_reclmation_show', array('id' => $reclamation->getId())));
        }

        return $this->render('ReclamationBundle:Reclamation:new.html.twig', array(
            'reclamation' => $reclamation,
            'form'   => $form->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing Reclamation entity.
     *
     */
    public function editAction(Reclamation $reclamation)
    {
        $editForm = $this->createForm(new ReclamationType(), $reclamation, array(
            'action' => $this->generateUrl('admin_reclmation_update', array('id' => $reclamation->getId())),
            'method' => 'PUT',
        ));
        $deleteForm = $this->createDeleteForm($reclamation->getId(), 'admin_reclmation_delete');

        return $this->render('ReclamationBundle:Reclamation:edit.html.twig', array(
            'reclamation' => $reclamation,
            'edit_form'   => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Edits an existing Reclamation entity.
     *
     */
    public function updateAction(Reclamation $reclamation, Request $request)
    {
        $editForm = $this->createForm(new ReclamationType(), $reclamation, array(
            'action' => $this->generateUrl('admin_reclmation_update', array('id' => $reclamation->getId())),
            'method' => 'PUT',
        ));
        if ($editForm->handleRequest($request)->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirect($this->generateUrl('admin_reclmation_edit', array('id' => $reclamation->getId())));
        }
        $deleteForm = $this->createDeleteForm($reclamation->getId(), 'admin_reclmation_delete');

        return $this->render('ReclamationBundle:Reclamation:edit.html.twig', array(
            'reclamation' => $reclamation,
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
        $this->setOrder('reclamation', $field, $type);

        return $this->redirect($this->generateUrl('admin_reclamation'));
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
        $session = $this->container->get('request_stack')->getCurrentRequest()
            ->getSession();

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
     * Deletes a Reclamation entity.
     *
     */
    public function deleteAction(Reclamation $reclamation, Request $request)
    {
        $form = $this->createDeleteForm($reclamation->getId(), 'admin_reclmation_delete');
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($reclamation);
            $em->flush();
        }

        return $this->redirect($this->generateUrl('admin_reclamation'));
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
