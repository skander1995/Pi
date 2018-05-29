<?php

namespace EvenementBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use EvenementBundle\Entity\Evenement;
use EvenementBundle\Form\Type\EvenementType;
use EvenementBundle\Form\Type\EvenementFilterType;
use Symfony\Component\Form\FormInterface;
use Doctrine\ORM\QueryBuilder;

/**
 * Evenement controller.
 *
 */
class AdminEvenementController extends Controller
{
    /**
     * Lists all Evenement entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $form = $this->createForm('EvenementBundle\Form\Type\EvenementFilterType');
        if (!is_null($response = $this->saveFilter($form, 'evenement', 'admin_evenement'))) {
            return $response;
        }
        $qb = $em->getRepository('EvenementBundle:Evenement')->createQueryBuilder('e');
        $paginator = $this->filter($form, $qb, 'evenement');
        return $this->render('EvenementBundle:Evenement:index.html.twig', array(
            'form' => $form->createView(),
            'paginator' => $paginator,
        ));
    }

    /**
     * Finds and displays a Evenement entity.
     *
     */
    public function showAction(Evenement $evenement)
    {
        $deleteForm = $this->createDeleteForm($evenement->getId(), 'admin_evenement_delete');

        return $this->render('EvenementBundle:Evenement:show.html.twig', array(
            'evenement' => $evenement,
            'delete_form' => $deleteForm->createView(),));
    }

    /**
     * Displays a form to create a new Evenement entity.
     *
     */
    public function newAction()
    {
        $evenement = new Evenement();
        $form = $this->createForm('EvenementBundle\Form\Type\EvenementType', $evenement);

        return $this->render('EvenementBundle:Evenement:new.html.twig', array(
            'evenement' => $evenement,
            'form' => $form->createView(),
        ));
    }

    /**
     * Creates a new Evenement entity.
     *
     */
    public function createAction(Request $request)
    {
        $evenement = new Evenement();
        $form = $this->createForm('EvenementBundle\Form\Type\EvenementType', $evenement);
        if ($form->handleRequest($request)->isValid()) {
            $evenement->setDatepub(new \DateTime());
            $em = $this->getDoctrine()->getManager();
            $em->persist($evenement);
            $em->flush();

            return $this->redirect($this->generateUrl('admin_evenement_show', array('id' => $evenement->getId())));
        }

        return $this->render('EvenementBundle:Evenement:new.html.twig', array(
            'evenement' => $evenement,
            'form' => $form->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing Evenement entity.
     *
     */
    public function editAction(Evenement $evenement)
    {
        $editForm = $this->createForm('EvenementBundle\Form\Type\EvenementType', $evenement, array(
            'action' => $this->generateUrl('admin_evenement_update', array('id' => $evenement->getId())),
            'method' => 'PUT',
        ));
        $deleteForm = $this->createDeleteForm($evenement->getId(), 'admin_evenement_delete');

        return $this->render('EvenementBundle:Evenement:edit.html.twig', array(
            'evenement' => $evenement,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Edits an existing Evenement entity.
     *
     */
    public function updateAction(Evenement $evenement, Request $request)
    {
        $editForm = $this->createForm('EvenementBundle\Form\Type\EvenementType', $evenement, array(
            'action' => $this->generateUrl('admin_evenement_update', array('id' => $evenement->getId())),
            'method' => 'PUT',
        ));
        if ($editForm->handleRequest($request)->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirect($this->generateUrl('admin_evenement_edit', array('id' => $evenement->getId())));
        }
        $deleteForm = $this->createDeleteForm($evenement->getId(), 'admin_evenement_delete');

        return $this->render('EvenementBundle:Evenement:edit.html.twig', array(
            'evenement' => $evenement,
            'edit_form' => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }


    /**
     * Save order.
     *
     */
    public function sortAction($field, $type)
    {
        $this->setOrder('evenement', $field, $type);

        return $this->redirect($this->generateUrl('admin_evenement'));
    }

    /**
     * @param string $name session name
     * @param string $field field name
     * @param string $type sort type ("ASC"/"DESC")
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
     * @param string $name
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
     * @param  string $name route/entity name
     * @param  string $route route name, if different from entity name
     * @param  array $params possible route parameters
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
     * @param  FormInterface $form
     * @param  QueryBuilder $qb
     * @param  string $name
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
        return $this->get('knp_paginator')->paginate($qb, $this->container->get('request_stack')->getCurrentRequest()
            ->query->get('page', 1), 20);
    }

    /**
     * Get filters from session
     *
     * @param  string $name
     * @return array
     */
    protected function getFilter($name)
    {
        return $this->container->get('request_stack')->getCurrentRequest()
            ->getSession()->get('filter.' . $name);
    }

    /**
     * Deletes a Evenement entity.
     *
     */
    public function deleteAction(Evenement $evenement, Request $request)
    {
        $form = $this->createDeleteForm($evenement->getId(), 'admin_evenement_delete');
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($evenement);
            $em->flush();
        }

        return $this->redirect($this->generateUrl('admin_evenement'));
    }

    /**
     * Create Delete form
     *
     * @param integer $id
     * @param string $route
     * @return \Symfony\Component\Form\Form
     */
    protected function createDeleteForm($id, $route)
    {
        return $this->createFormBuilder(null, array('attr' => array('id' => 'delete')))
            ->setAction($this->generateUrl($route, array('id' => $id)))
            ->setMethod('DELETE')
            ->getForm();
    }

}
