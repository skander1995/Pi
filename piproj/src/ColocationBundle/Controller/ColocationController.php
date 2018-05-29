<?php

namespace ColocationBundle\Controller;

use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;

use ColocationBundle\Entity\Colocation;
use ColocationBundle\Form\Type\ColocationType;
use ColocationBundle\Form\Type\ColocationFilterType;
use Symfony\Component\Form\FormInterface;
use Doctrine\ORM\QueryBuilder;

/**
 * Colocation controller.
 *
 */
class ColocationController extends Controller
{
    /**
     * Lists all Colocation entities.
     *
     */
    public function indexAction()
    {
        $em = $this->getDoctrine()->getManager();
        $form = $this->createForm(new ColocationFilterType());
        if (!is_null($response = $this->saveFilter($form, 'colocation', 'admin_colocation'))) {
            return $response;
        }
        $qb = $em->getRepository('ColocationBundle:Colocation')->createQueryBuilder('c');
        $paginator = $this->filter($form, $qb, 'colocation');
                return $this->render('ColocationBundle:Colocation:index.html.twig', array(
            'form'      => $form->createView(),
            'paginator' => $paginator,
        ));
    }

    /**
     * Finds and displays a Colocation entity.
     *
     */
    public function showAction(Colocation $colocation)
    {
        $deleteForm = $this->createDeleteForm($colocation->getId(), 'admin_colocation_delete');

        return $this->render('ColocationBundle:Colocation:show.html.twig', array(
            'colocation' => $colocation,
            'delete_form' => $deleteForm->createView(),        ));
    }

    /**
     * Displays a form to create a new Colocation entity.
     *
     */
    public function newAction()
    {
        $colocation = new Colocation();
        $form = $this->createForm(new ColocationType(), $colocation);

        return $this->render('ColocationBundle:Colocation:new.html.twig', array(
            'colocation' => $colocation,
            'form'   => $form->createView(),
        ));
    }

    /**
     * Creates a new Colocation entity.
     *
     */
    public function createAction(Request $request)
    {
        $colocation = new Colocation();
        $form = $this->createForm(new ColocationType(), $colocation);
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($colocation);
            $em->flush();

            return $this->redirect($this->generateUrl('admin_colocation_show', array('id' => $colocation->getId())));
        }

        return $this->render('ColocationBundle:Colocation:new.html.twig', array(
            'colocation' => $colocation,
            'form'   => $form->createView(),
        ));
    }

    /**
     * Displays a form to edit an existing Colocation entity.
     *
     */
    public function editAction(Colocation $colocation)
    {
        $editForm = $this->createForm(new ColocationType(), $colocation, array(
            'action' => $this->generateUrl('admin_colocation_update', array('id' => $colocation->getId())),
            'method' => 'PUT',
        ));
        $deleteForm = $this->createDeleteForm($colocation->getId(), 'admin_colocation_delete');

        return $this->render('ColocationBundle:Colocation:edit.html.twig', array(
            'colocation' => $colocation,
            'edit_form'   => $editForm->createView(),
            'delete_form' => $deleteForm->createView(),
        ));
    }

    /**
     * Edits an existing Colocation entity.
     *
     */
    public function updateAction(Colocation $colocation, Request $request)
    {
        $editForm = $this->createForm(new ColocationType(), $colocation, array(
            'action' => $this->generateUrl('admin_colocation_update', array('id' => $colocation->getId())),
            'method' => 'PUT',
        ));
        if ($editForm->handleRequest($request)->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirect($this->generateUrl('admin_colocation_edit', array('id' => $colocation->getId())));
        }
        $deleteForm = $this->createDeleteForm($colocation->getId(), 'admin_colocation_delete');

        return $this->render('ColocationBundle:Colocation:edit.html.twig', array(
            'colocation' => $colocation,
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
        $this->setOrder('colocation', $field, $type);

        return $this->redirect($this->generateUrl('admin_colocation'));
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
        return $this->container->get('request_stack')->getCurrentRequest()
            ->getSession()->get('filter.' . $name);
    }

    /**
     * Deletes a Colocation entity.
     *
     */
    public function deleteAction(Colocation $colocation, Request $request)
    {
        $form = $this->createDeleteForm($colocation->getId(), 'admin_colocation_delete');
        if ($form->handleRequest($request)->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->remove($colocation);
            $em->flush();
        }

        return $this->redirect($this->generateUrl('admin_colocation'));
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
